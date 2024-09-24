package com.parc.api.service;

import com.parc.api.model.dto.CommentaireDto;
import com.parc.api.model.entity.Commentaire;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.entity.Utilisateur;
import com.parc.api.model.mapper.CommentaireMapper;
import com.parc.api.repository.CommentaireRepository;
import com.parc.api.repository.ParcRepository;
import com.parc.api.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentaireService {

    private final CommentaireRepository commentaireRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ParcRepository parcRepository;



    public ResponseEntity<List<CommentaireDto>> getAllCommentaire() {
        List<Commentaire> commentaireList = commentaireRepository.findAll();
        List<CommentaireDto> commentaireDtos = commentaireList.stream()
                .map(CommentaireMapper::toDto).toList();
        return ResponseEntity.ok(commentaireDtos);
    }

    public ResponseEntity<CommentaireDto> getCommentaireById(@PathVariable int id) {
        Optional<Commentaire> CommentaireOptional = commentaireRepository.findById(id);
        if (CommentaireOptional.isPresent()) {
            CommentaireDto CommentaireDto = CommentaireMapper.toDto(CommentaireOptional.get());
            return ResponseEntity.ok(CommentaireDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<CommentaireDto> createCommentaire(@RequestBody CommentaireDto commentaireDto) {
        if (commentaireDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    
        Commentaire commentaire = CommentaireMapper.toEntity(commentaireDto);

        Commentaire savedCommentaire = commentaireRepository.save(commentaire);
        CommentaireDto savedCommentaireDto = CommentaireMapper.toDto(savedCommentaire);
        return new ResponseEntity<>(savedCommentaireDto, HttpStatus.CREATED);
      
    }

    public ResponseEntity<CommentaireDto> updateCommentaire(@PathVariable int id, @RequestBody CommentaireDto commentaireDto) {
        Optional<Commentaire> foundCommentaireOptional = commentaireRepository.findById(id);
        if (foundCommentaireOptional.isPresent()) {
            Commentaire foundCommentaire = foundCommentaireOptional.get();
            foundCommentaire.setTextCommentaire(commentaireDto.getContenuCommentaire());
            foundCommentaire.setNote(commentaireDto.getNoteParc());
            Commentaire savedCommentaire = commentaireRepository.save(foundCommentaire);
            CommentaireDto updatedCommentaireDto = CommentaireMapper.toDto(savedCommentaire);
            return ResponseEntity.ok(updatedCommentaireDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<CommentaireDto> deleteCommentaire(@PathVariable int id) {
        Optional<Commentaire> CommentaireOptional = commentaireRepository.findById(id);
        if (CommentaireOptional.isPresent()) {
            commentaireRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<CommentaireDto> createCommentaireWithIds(int idParc, int idUtilisateur, String contenu, int note) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(idUtilisateur);
        Optional<Parc> parcOptional = parcRepository.findById(idParc);

        if (utilisateurOptional.isEmpty() || parcOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Utilisateur utilisateur = utilisateurOptional.get();
        Parc parc = parcOptional.get();

        Commentaire commentaire = new Commentaire();
        commentaire.setIdUtilisateur(utilisateur);
        commentaire.setIdParc(parc);
        commentaire.setTextCommentaire(contenu);
        commentaire.setNote(note);

        Commentaire savedCommentaire = commentaireRepository.save(commentaire);
        CommentaireDto savedCommentaireDto = CommentaireMapper.toDto(savedCommentaire);

        return new ResponseEntity<>(savedCommentaireDto, HttpStatus.CREATED);
    }
}



