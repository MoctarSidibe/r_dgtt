package ga.dgtt.usager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant une permission dans le système
 */
@Entity
@Table(name = "permissions")
@EntityListeners(AuditingEntityListener.class)
public class Permission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom de la permission est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    @Column(name = "nom", nullable = false, unique = true)
    private String nom;
    
    @NotBlank(message = "La description est obligatoire")
    @Size(max = 255, message = "La description ne peut pas dépasser 255 caractères")
    @Column(name = "description", nullable = false)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "categorie", nullable = false)
    private CategoriePermission categorie;
    
    @Column(name = "est_active")
    private Boolean estActive = true;
    
    @CreatedDate
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    
    @LastModifiedDate
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private List<Usager> usagers = new ArrayList<>();
    
    // Constructeurs
    public Permission() {}
    
    public Permission(String nom, String description, CategoriePermission categorie) {
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public CategoriePermission getCategorie() { return categorie; }
    public void setCategorie(CategoriePermission categorie) { this.categorie = categorie; }
    
    public Boolean getEstActive() { return estActive; }
    public void setEstActive(Boolean estActive) { this.estActive = estActive; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
    
    public List<Usager> getUsagers() { return usagers; }
    public void setUsagers(List<Usager> usagers) { this.usagers = usagers; }
}
