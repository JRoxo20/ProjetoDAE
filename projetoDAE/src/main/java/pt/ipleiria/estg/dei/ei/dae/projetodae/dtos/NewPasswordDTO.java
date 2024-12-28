package pt.ipleiria.estg.dei.ei.dae.projetodae.dtos;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class NewPasswordDTO implements Serializable {

    @NotBlank
    private String newPassword;
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String confirmNewPassword;

    public NewPasswordDTO() {
    }

    public NewPasswordDTO(String newPassword, String oldPassword, String confirmNewPassword) {
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
