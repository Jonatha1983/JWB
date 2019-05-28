package com.gafner.jwb.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gafner.jwb.client.conversation.GroupConversation;
import com.gafner.jwb.client.paint_operation.DrawOperation;
import com.gafner.jwb.client.paint_operation.JWBDraw;
import com.gafner.jwb.api.service.group.GroupConnectionService;
import com.gafner.jwb.api.service.users.UserConnectionService;
import com.gafner.jwb.client.toggle.JWBCToggleManager;
import com.gafner.jwb.client.utils.PairUserMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;


@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "unused"})
@Component
public class CanvasController implements Initializable {
    @FXML
    public BorderPane borderPane;

    @FXML
    public StackPane stackPane;
    @FXML
    public StackPane canvasPaneHolder;

    public TextArea chatArea;
    public TextField chatField;
    public Button sendMessage;

    @FXML
    private Canvas canvas;

    @Autowired
    private HomeController homeController;

    @Autowired
    private GroupConnectionService clientGroupConnectionService;

    @Autowired
    private UserConnectionService userConnectionService;

    @Autowired
    private JWBCController jwbcController;

    private ClientListener clientListener;

    private JWBCToggleManager toggleManager;
    private ObjectMapper objectMapper;

    private JWBDraw jwbDraw;
    private GroupConversation groupConversation;

    private String groupName;
    private String userName;
    private boolean initialize;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.jwbDraw = new JWBDraw(canvas.getGraphicsContext2D());
        this.groupConversation = new GroupConversation();
        this.toggleManager = new JWBCToggleManager(canvas,
                jwbcController.getEditButton(),
                jwbcController.getEraseButton(),
                jwbcController.getRectangleButton(),
                jwbcController.getLineButton(),
                jwbcController.getCircleButton(),
                jwbcController.getComboBoxLineThickens(),
                jwbcController.getColorPicker(),
                jwbcController.getColorPickerFill());
        this.initialize = true;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enableDefaultTyping();
        this.chatArea.setEditable(false);
        try {
            this.clientListener = new ClientListener(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void mousePressed(MouseEvent mouseEvent) {
        toggleManager.withCanvasPressedDo(canvasPaneHolder, mouseEvent);
    }

    @FXML
    public void mouseDragged(MouseEvent mouseEvent) {
        toggleManager.withCanvasDraggedDo(canvasPaneHolder, mouseEvent);
    }

    @FXML
    public void mouseReleased(MouseEvent mouseEvent) {
        DrawOperation DrawOperation = toggleManager.withCanvasReleaseDo(canvasPaneHolder, mouseEvent);
        if (DrawOperation != null) {
            jwbDraw.addDrawOperation(DrawOperation);
            updateGroupPaint();
        }
    }

    @FXML
    public void mouseEntered(MouseEvent mouseEvent) {
        toggleManager.withEnteredSetCursor();
    }

    @FXML
    public void onMouseExited(MouseEvent mouseEvent) {
        toggleManager.withExitedResetCursor();
    }

    @FXML
    public void sendMessage(ActionEvent actionEvent) {
        if (userName == null) {
            this.userName = userConnectionService.getUserByEmail(homeController.getUsedEmail());
        }
        PairUserMessage pairUserMessage = PairUserMessage.create(userName, chatField.getText());
        groupConversation.addPair(pairUserMessage);
        chatField.setText("");
        updateGroupChat();
    }

    void undo() {
        jwbDraw.undo();
        updateGroupPaint();
    }

    void redo() {
        jwbDraw.redo();
        updateGroupPaint();
    }

    /**
     * If group does not exists set the canvas groupName
     *
     * @param groupName -
     * @return true if new group was set and created successfully
     */
    boolean newGroup(String groupName) {
        try {
            String jwbDrawJson = objectMapper.writeValueAsString(jwbDraw);
            String conversationJson = objectMapper.writeValueAsString(groupConversation);
            if (clientGroupConnectionService.createGroup(groupName, jwbDrawJson, conversationJson, clientListener)) {
                this.groupName = groupName;
                return true;
            } else {
                return false;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not serialize paint", e);
        }
    }

    boolean joinGroup(String groupName) {
        this.groupName = groupName;
        if (clientGroupConnectionService.joinGroup(groupName, clientListener)) {
            loadFromDB();
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    List<String> getExistingGroup() {
        return clientGroupConnectionService.getAllGroupsName();
    }

    private void loadFromDB() {
        loadPaintFromDB();
        loadConversationFromDB();

    }

    void loadConversationFromDB() {
        String jsonConversation = clientGroupConnectionService.loadConversation(groupName, clientListener);

        try {
            groupConversation = objectMapper.readValue(jsonConversation, GroupConversation.class);
            chatArea.clear();
            chatArea.appendText("");
            groupConversation.getPairUserMessageList().forEach(p -> chatArea.appendText("\n" + p.toString())
            );

        } catch (IOException e) {
            //todo temp code
            throw new RuntimeException("Could not deserialize", e);
        }

    }

    void loadPaintFromDB() {
        String jsonPaint = clientGroupConnectionService.loadPaint(groupName, clientListener);

        InjectableValues inject = new InjectableValues.Std().addValue(GraphicsContext.class, canvas.getGraphicsContext2D());

        try {
            jwbDraw = objectMapper.reader(inject).forType(JWBDraw.class).readValue(jsonPaint);

        } catch (IOException e) {
            //todo temp code
            throw new RuntimeException("Could not deserialize", e);
        }
        jwbDraw.redrawFromLoad();
    }

    void update() {
        loadFromDB();
    }

    private void updateGroupPaint() {
        try {
            String jwbDrawJson = objectMapper.writeValueAsString(jwbDraw);
            clientGroupConnectionService.updateGroupPaint(groupName, jwbDrawJson, clientListener);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not serialize pain", e);
        }
    }

    private void updateGroupChat() {
        try {
            String groupConversationJson = objectMapper.writeValueAsString(groupConversation);
            clientGroupConnectionService.updateGroupConversation(groupName, groupConversationJson, clientListener);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not serialize pain", e);
        }
    }

    public boolean isInitialize() {
        return initialize;
    }
}
