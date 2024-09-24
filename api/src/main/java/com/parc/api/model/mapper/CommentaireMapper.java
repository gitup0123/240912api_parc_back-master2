
package com.parc.api.model.mapper;

import com.parc.api.model.dto.CommentaireDto;
import com.parc.api.model.dto.ParcDto;
import com.parc.api.model.dto.UtilisateurDto;
import com.parc.api.model.entity.Commentaire;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.entity.Utilisateur;

public class CommentaireMapper  {

    public static CommentaireDto toDto(Commentaire commentaire) {

        ParcDto parcDto = ParcMapper.toDto(commentaire.getIdParc());
        UtilisateurDto utilisateurDto = UtilisateurMapper.toDto(commentaire.getIdUtilisateur());

        CommentaireDto commentaireDto = new CommentaireDto();
        commentaireDto.setIdCommentaire(commentaire.getId());
        commentaireDto.setContenuCommentaire(commentaire.getTextCommentaire());
        commentaireDto.setNoteParc(commentaire.getNote());
        commentaireDto.setIdParc(parcDto);
        commentaireDto.setIdUtilisateur(utilisateurDto);
        return commentaireDto;
    }
    public static Commentaire toEntity(CommentaireDto commentaireDto) {

        Parc parc = ParcMapper.toEntity(commentaireDto.getIdParc());
        Utilisateur utilisateur = UtilisateurMapper.toEntity(commentaireDto.getIdUtilisateur());

        Commentaire commentaire = new Commentaire();
        commentaire.setId(commentaireDto.getIdCommentaire());
        commentaire.setTextCommentaire(commentaireDto.getContenuCommentaire());
        commentaire.setNote(commentaireDto.getNoteParc());
        commentaire.setIdUtilisateur(utilisateur);
        commentaire.setIdParc(parc);
        return commentaire;
    }

}


