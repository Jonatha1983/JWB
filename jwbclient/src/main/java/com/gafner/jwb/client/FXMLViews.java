package com.gafner.jwb.client;

public enum FXMLViews {

    HOME {
        @Override
        public String getTitle() {
            return "OpenU White Board";
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/home.fxml";
        }
    },
    PAINT_CHOOSER {
        @Override
        public String getTitle() {
            return "JWBC";
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/jwbc.fxml";
        }

    };

    public abstract String getTitle();

    public abstract String getFxmlFile();

}
