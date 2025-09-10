package fromzerotohero;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;

@Named("loginUser")
@SessionScoped
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String loginErrorMsg;
    private boolean loggedIn;
    private String role; // "SCI" oder "ADMIN"

    private static String norm(String s) {
        return s == null ? "" : s.trim().toLowerCase();
    }

    /** Ajax-Login (falls du ihn noch brauchst) â€“ bleibt auf derselben View */
    public String login() {
        String u = norm(username);
        String p = norm(password);

        if ("admin".equals(u) && "admin".equals(p)) {
            loggedIn = true; role = "ADMIN"; password = null; loginErrorMsg = null;
            return null;
        } else if (("wissenschaftler".equals(u) || "wissenschaftlerin".equals(u)) && "wissen".equals(p)) {
            loggedIn = true; role = "SCI"; password = null; loginErrorMsg = null;
            return null;
        } else {
            loggedIn = false; role = null; loginErrorMsg = "Login fehlerhaft";
            return null;
        }
    }

    /** Navigation von index.xhtml nach Rolle */
    public String loginNavigate() {
        String u = norm(username);
        String p = norm(password);

        if ("admin".equals(u) && "admin".equals(p)) {
            loggedIn = true; role = "ADMIN"; password = null; loginErrorMsg = null;
            return "admin?faces-redirect=true";
        } else if (("wissenschaftler".equals(u) || "wissenschaftlerin".equals(u)) && "wissen".equals(p)) {
            loggedIn = true; role = "SCI"; password = null; loginErrorMsg = null;
            return "login?faces-redirect=true";
        } else {
            loggedIn = false; role = null; loginErrorMsg = "Login fehlerhaft";
            return null;
        }
    }

    public String logout() {
        loggedIn = false; role = null; username = null; password = null;
        return "index?faces-redirect=true";
    }

    // Guards fÃ¼r Seiten
    public String guardEditor() { return isCanEdit() ? null : "index?faces-redirect=true"; }
    public String guardAdmin()  { return isAdmin()   ? null : "index?faces-redirect=true"; }

    // Getter/Flags
    public boolean isLoggedIn() { return loggedIn; }
    public boolean isCanEdit() { return loggedIn && ("SCI".equals(role) || "ADMIN".equals(role)); }
    public boolean isAdmin()   { return "ADMIN".equals(role); }
    public String getRole()    { return role; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getLoginErrorMsg() { return loginErrorMsg; }
    public void setLoginErrorMsg(String loginErrorMsg) { this.loginErrorMsg = loginErrorMsg; }
}




