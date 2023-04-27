package ija.ija2022.project.ui;

public class UIController {
    private static UIController instance;
    private IUIView currentView;

    private UIController() {
        currentView = new Window();
    }

    public static UIController getInstance() {
        if (UIController.instance == null) {
            UIController.instance = new UIController();
        }

        return UIController.instance;
    }

    public void showMainView() {
        currentView.dispose();
        currentView = new Window();
        currentView.getFrame().setVisible(true);
    }

    public void showSettingsView() {
        currentView.dispose();
        currentView = new SettingsView();
        currentView.getFrame().setVisible(true);
    }
}
