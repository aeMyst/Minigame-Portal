module src.ca.ucalgary.seng300 {
    exports src.ca.ucalgary.seng300.gameApp;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
//    opens src.ca.ucalgary.seng300 to javafx.fxml;
    exports src.ca.ucalgary.seng300;
    exports src.ca.ucalgary.seng300.gameApp.leaderboardScreens;
    exports src.ca.ucalgary.seng300.gameApp.gameScreens;
    exports src.ca.ucalgary.seng300.gameApp.loadingScreens;
    exports src.ca.ucalgary.seng300.gameApp.accountScreens;
    exports src.ca.ucalgary.seng300.gameApp.menuScreens;
    exports src.ca.ucalgary.seng300.gameApp.extraScreens;
    exports src.ca.ucalgary.seng300.network;
    opens src.ca.ucalgary.seng300.network to javafx.fxml;
    exports src.ca.ucalgary.seng300.gameApp.Utility;

    // resources
    opens src.ca.ucalgary.seng300;
}