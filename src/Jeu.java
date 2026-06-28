import MG2D.Fenetre;
import MG2D.Couleur;
import MG2D.Souris;
import MG2D.geometrie.Point;
import MG2D.geometrie.Rectangle;
import MG2D.geometrie.Cercle;
import MG2D.geometrie.Texte;
import MG2D.geometrie.Texture;
import java.awt.Font;
import java.awt.Color;

/**
 * Classe principale du jeu.
 *
 * COORDONNÉES MG2D : origine (0,0) = BAS GAUCHE, Y monte vers le HAUT.
 * Fenêtre : 600 x 700px
 *   - Bandeau haut : Y de 600 à 700 (100px)
 *   - Grille      : Y de 0   à 600 (600px) = 9 cases de 66px chacune
 */
public class Jeu {

    static final int LARGEUR      = 600;
    static final int HAUTEUR      = 700;
    static final int TAILLE_GRILLE = 600;
    static final int TAILLE_CASE  = TAILLE_GRILLE / Plateau.TAILLE; // 66px

    private Fenetre fenetre;
    private Souris  souris;
    private Plateau plateau;

    private int  joueurActuel  = 1;   // 1=rouge, 2=vert
    private boolean partieTerminee = false;
    private int  gagnant       = 0;

    // Texte du bandeau
    private Texte texteTour;

    // Tableaux graphiques [ligne][colonne]
    private Texture[][] textures = new Texture[Plateau.TAILLE][Plateau.TAILLE];
    private Texte[][]   textesPV = new Texte[Plateau.TAILLE][Plateau.TAILLE];
    // Cercles de sélection / mouvement (créés une fois, ajoutés/supprimés dynamiquement)
    private Cercle[][]  cercles  = new Cercle[Plateau.TAILLE][Plateau.TAILLE];
    private boolean[][] cercleVisible = new boolean[Plateau.TAILLE][Plateau.TAILLE];

    public Jeu() {
        plateau = new Plateau();
        fenetre = new Fenetre("Pokémon Échecs", LARGEUR, HAUTEUR);
        souris  = fenetre.getSouris();

        construireInterface();
        dessinerPlateau();
        fenetre.rafraichir();
        boucleJeu();
    }

    // ============================================================
    //  CONSTRUCTION DE L'INTERFACE (une seule fois au démarrage)
    // ============================================================
    private void construireInterface() {

        // --- Fond blanc pour la grille ---
        fenetre.ajouter(new Rectangle(
            new Couleur(Color.WHITE),
            new Point(0, 0),
            new Point(TAILLE_GRILLE, TAILLE_GRILLE),
            true));

        // --- Bandeau bleu ---
        fenetre.ajouter(new Rectangle(
            new Couleur(Color.decode("#5BC8F5")),
            new Point(0, TAILLE_GRILLE),
            new Point(LARGEUR, HAUTEUR),
            true));

        // --- Texte du tour ---
        texteTour = new Texte(
            new Couleur(Color.RED),
            "Au joueur rouge de jouer",
            new Font("Arial", Font.BOLD, 26),
            new Point(LARGEUR / 2, HAUTEUR - 55));
        fenetre.ajouter(texteTour);

        // --- Lignes de la grille ---
        Couleur gris = new Couleur(Color.LIGHT_GRAY);
        for (int i = 0; i <= Plateau.TAILLE; i++) {
            int y = i * TAILLE_CASE;
            fenetre.ajouter(new Rectangle(gris,
                new Point(0, y), new Point(TAILLE_GRILLE, y + 1), true));
            int x = i * TAILLE_CASE;
            fenetre.ajouter(new Rectangle(gris,
                new Point(x, 0), new Point(x + 1, TAILLE_GRILLE), true));
        }

        // --- Pré-créer les cercles (déplacés via translater) ---
        Font fontPV = new Font("Arial", Font.BOLD, 13);
        for (int l = 0; l < Plateau.TAILLE; l++) {
            for (int c = 0; c < Plateau.TAILLE; c++) {
                int cx = c * TAILLE_CASE + TAILLE_CASE / 2;
                int cy = (Plateau.TAILLE - 1 - l) * TAILLE_CASE + TAILLE_CASE / 2;

                // Cercle pour sélection / mouvement
                cercles[l][c] = new Cercle(
                    new Couleur(Color.WHITE),
                    new Point(cx, cy),
                    TAILLE_CASE / 2 - 5,
                    false);
                cercleVisible[l][c] = false;

                // Texte PV (vide au départ)
                textesPV[l][c] = new Texte(
                    new Couleur(Color.GREEN),
                    "",
                    fontPV,
                    new Point(cx - 8, (Plateau.TAILLE - 1 - l) * TAILLE_CASE + 3));
            }
        }
    }

    // ============================================================
    //  BOUCLE PRINCIPALE
    // ============================================================
    private void boucleJeu() {
        while (!partieTerminee) {
            try { Thread.sleep(16); } catch (Exception e) {}

            if (souris.getClicGauche()) {
                Point pos = souris.getPosition();
                int px = (int) pos.getX();
                int py = (int) pos.getY();

                // Clic dans la grille ?
                if (px >= 0 && px < TAILLE_GRILLE && py >= 0 && py < TAILLE_GRILLE) {
                    int ligne   = (Plateau.TAILLE - 1) - (py / TAILLE_CASE);
                    int colonne = px / TAILLE_CASE;
                    if (ligne >= 0 && ligne < Plateau.TAILLE &&
                        colonne >= 0 && colonne < Plateau.TAILLE) {
                        gererClic(ligne, colonne);
                    }
                }
            }

            fenetre.rafraichir();
        }

        // Message victoire
        fenetre.ajouter(new Texte(
            new Couleur(gagnant == 1 ? Color.RED : Color.GREEN),
            (gagnant == 1 ? "JOUEUR ROUGE" : "JOUEUR VERT") + " GAGNE !",
            new Font("Arial", Font.BOLD, 38),
            new Point(LARGEUR / 2, HAUTEUR / 2)));
        fenetre.rafraichir();

        try { Thread.sleep(5000); } catch (Exception e) {}
        fenetre.fermer();
    }

    // ============================================================
    //  GESTION DU CLIC
    // ============================================================
    private void gererClic(int ligne, int colonne) {
        Case caseCliquee = plateau.getCase(ligne, colonne);

        if (plateau.aUneSelection()) {

            if (caseCliquee.isMouvementPossible()) {
                // ► Action (déplacement ou attaque)
                boolean fin = plateau.effectuerAction(ligne, colonne, joueurActuel);
                dessinerPlateau();
                if (fin || !plateau.mewtwoVivant(3 - joueurActuel)) {
                    partieTerminee = true;
                    gagnant = joueurActuel;
                    return;
                }
                changerTour();

            } else if (!caseCliquee.estVide() && caseCliquee.getJoueur() == joueurActuel) {
                // ► Changer de sélection
                plateau.selectionner(ligne, colonne, joueurActuel);
                dessinerPlateau();

            } else {
                // ► Désélectionner
                plateau.effacerSelection();
                dessinerPlateau();
            }

        } else {
            // ► Sélectionner un pokémon
            if (!caseCliquee.estVide() && caseCliquee.getJoueur() == joueurActuel) {
                plateau.selectionner(ligne, colonne, joueurActuel);
                dessinerPlateau();
            }
        }
    }

    // ============================================================
    //  CHANGER DE TOUR
    // ============================================================
    private void changerTour() {
        joueurActuel = (joueurActuel == 1) ? 2 : 1;
        if (joueurActuel == 1) {
            texteTour.setTexte("Au joueur rouge de jouer");
            texteTour.setCouleur(new Couleur(Color.RED));
        } else {
            texteTour.setTexte("Au joueur vert de jouer");
            texteTour.setCouleur(new Couleur(Color.GREEN));
        }
    }

    // ============================================================
    //  DESSINER LE PLATEAU COMPLET
    // ============================================================
    private void dessinerPlateau() {

        // 1) Supprimer les anciens éléments dynamiques
        for (int l = 0; l < Plateau.TAILLE; l++) {
            for (int c = 0; c < Plateau.TAILLE; c++) {
                if (textures[l][c] != null) {
                    fenetre.supprimer(textures[l][c]);
                    textures[l][c] = null;
                }
                fenetre.supprimer(textesPV[l][c]);
                if (cercleVisible[l][c]) {
                    fenetre.supprimer(cercles[l][c]);
                    cercleVisible[l][c] = false;
                }
            }
        }

        // 2) Redessiner case par case
        for (int l = 0; l < Plateau.TAILLE; l++) {
            for (int c = 0; c < Plateau.TAILLE; c++) {
                Case cas = plateau.getCase(l, c);

                // Coordonnées pixel (coin bas-gauche de la case)
                int px = c * TAILLE_CASE;
                int py = (Plateau.TAILLE - 1 - l) * TAILLE_CASE;
                int cx = px + TAILLE_CASE / 2;
                int cy = py + TAILLE_CASE / 2;

                // --- Cercle mouvement possible (blanc) ou sélectionné (rouge) ---
                boolean selectionne = (plateau.getLigneSelectionnee() == l &&
                                       plateau.getColonneSelectionnee() == c);
                if (cas.isMouvementPossible() || selectionne) {
                    cercles[l][c].setCouleur(
                        new Couleur(selectionne ? Color.RED : Color.WHITE));
                    fenetre.ajouter(cercles[l][c]);
                    cercleVisible[l][c] = true;
                }

                // --- Image du Pokémon ---
                if (!cas.estVide()) {
                    Pokemon pok = cas.getPokemon();

                    textures[l][c] = new Texture(
                        "images/" + pok.getNumPokedex() + ".png",
                        new Point(px + 2, py + 14),
                        TAILLE_CASE - 4,
                        TAILLE_CASE - 18);
                    fenetre.ajouter(textures[l][c]);

                    // --- Texte PV ---
                    int pv    = pok.getPv();
                    int pvMax = pok.getPvMax();
                    textesPV[l][c].setTexte(String.valueOf(pv));
                    textesPV[l][c].setA(new Point(cx - 8, py + 3));
                    textesPV[l][c].setCouleur(
                        new Couleur(pv > pvMax / 2 ? Color.GREEN : Color.RED));
                    fenetre.ajouter(textesPV[l][c]);
                }
            }
        }
    }
}
