package com.parc.api.model.dto;



import lombok.*;


@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentaireDto {

    private int idCommentaire;
    private String contenuCommentaire;
    private int noteParc;
    private ParcDto idParc;
    private UtilisateurDto idUtilisateur;

}
