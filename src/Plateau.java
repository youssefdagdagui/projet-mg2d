// classe qui represente le plateau de jeu 9x9

public class Plateau {

    public static final int TAILLE = 9;

    private Case[][] grille;

    // pour savoir quel pokemon est selectionne
    private int ligneSelect;
    private int colSelect;

    public Plateau() {
        grille = new Case[TAILLE][TAILLE];

        // initialiser toutes les cases
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                grille[i][j] = new Case();
            }
        }

        ligneSelect = -1;
        colSelect = -1;

        placerPokemons();
    }

    // on place les pokemons au debut de la partie
    private void placerPokemons() {

        // joueur rouge en haut (lignes 0, 1, 2)
        int[] ligne0 = {4, 7, 1, 59, 150, 145, 1, 7, 4};
        for (int c = 0; c < TAILLE; c++) {
            Pokemon p = new Pokemon(ligne0[c], Type.getEspece(ligne0[c]));
            grille[0][c].setPokemon(p, 1);
        }

        int[] ligne1 = {4, 7, 1, 78, 130, 149, 1, 7, 4};
        for (int c = 0; c < TAILLE; c++) {
            Pokemon p = new Pokemon(ligne1[c], Type.getEspece(ligne1[c]));
            grille[1][c].setPokemon(p, 1);
        }

        // quelques pokemons avances au milieu pour le rouge
        int[] ids2 = {3, 76, 6};
        int[] cols2 = {3, 4, 5};
        for (int i = 0; i < ids2.length; i++) {
            Pokemon p = new Pokemon(ids2[i], Type.getEspece(ids2[i]));
            grille[2][cols2[i]].setPokemon(p, 1);
        }

        // joueur vert en bas (lignes 6, 7, 8)
        int[] ids6 = {6, 76, 3};
        int[] cols6 = {3, 4, 5};
        for (int i = 0; i < ids6.length; i++) {
            Pokemon p = new Pokemon(ids6[i], Type.getEspece(ids6[i]));
            grille[6][cols6[i]].setPokemon(p, 2);
        }

        int[] ligne7 = {4, 7, 1, 78, 130, 149, 1, 7, 4};
        for (int c = 0; c < TAILLE; c++) {
            Pokemon p = new Pokemon(ligne7[c], Type.getEspece(ligne7[c]));
            grille[7][c].setPokemon(p, 2);
        }

        int[] ligne8 = {4, 7, 1, 59, 150, 145, 1, 7, 4};
        for (int c = 0; c < TAILLE; c++) {
            Pokemon p = new Pokemon(ligne8[c], Type.getEspece(ligne8[c]));
            grille[8][c].setPokemon(p, 2);
        }
    }

    public Case getCase(int ligne, int col) { return grille[ligne][col]; }
    public int getLigneSelectionnee() { return ligneSelect; }
    public int getColonneSelectionnee() { return colSelect; }
    public boolean aUneSelection() { return ligneSelect != -1; }

    // selectionner un pokemon et afficher les cases ou il peut aller
    public void selectionner(int l, int c, int joueur) {
        effacerMouvements();

        Case ca = grille[l][c];

        if (!ca.estVide() && ca.getJoueur() == joueur) {
            ligneSelect = l;
            colSelect = c;
            calculerMouvements(l, c, joueur);
        } else {
            ligneSelect = -1;
            colSelect = -1;
        }
    }

    // trouver toutes les cases accessibles autour du pokemon selectionne
    private void calculerMouvements(int l, int c, int joueur) {
        for (int dl = -1; dl <= 1; dl++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dl == 0 && dc == 0) continue;

                int nl = l + dl;
                int nc = c + dc;

                // verifier que la case est dans le plateau
                if (nl >= 0 && nl < TAILLE && nc >= 0 && nc < TAILLE) {
                    Case voisine = grille[nl][nc];
                    // case vide ou ennemi = on peut y aller
                    if (voisine.estVide() || voisine.getJoueur() != joueur) {
                        voisine.setMouvementPossible(true);
                    }
                }
            }
        }
    }

    // effectuer le mouvement ou lattaque apres avoir clique sur une case
    public boolean effectuerAction(int l, int c, int joueur) {
        if (!aUneSelection()) return false;
        if (!grille[l][c].isMouvementPossible()) return false;

        Case depart = grille[ligneSelect][colSelect];
        Case arrivee = grille[l][c];
        Pokemon attaquant = depart.getPokemon();
        boolean fin = false;

        if (arrivee.estVide()) {
            // deplacement simple
            arrivee.setPokemon(attaquant, joueur);
            depart.vider();
        } else {
            // combat !
            Pokemon defenseur = arrivee.getPokemon();

            attaquant.attaque(defenseur);

            // verifier si le defenseur est mort
            if (!defenseur.estVivant()) {
                if (defenseur.getNumPokedex() == 150) fin = true; // mewtwo mort = fin
                arrivee.setPokemon(attaquant, joueur);
                depart.vider();
            }

            // verifier si lattaquant est mort aussi (contre-attaque)
            if (!attaquant.estVivant()) {
                if (attaquant.getNumPokedex() == 150) fin = true;
                depart.vider();
                if (!defenseur.estVivant()) {
                    arrivee.vider(); // les deux sont morts
                }
            }
        }

        effacerSelection();
        return fin;
    }

    public void effacerMouvements() {
        for (int i = 0; i < TAILLE; i++)
            for (int j = 0; j < TAILLE; j++)
                grille[i][j].setMouvementPossible(false);
    }

    public void effacerSelection() {
        effacerMouvements();
        ligneSelect = -1;
        colSelect = -1;
    }

    // verifier si le mewtwo du joueur est encore vivant
    public boolean mewtwoVivant(int joueur) {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                Case ca = grille[i][j];
                if (!ca.estVide() && ca.getJoueur() == joueur
                    && ca.getPokemon().getNumPokedex() == 150
                    && ca.getPokemon().estVivant()) {
                    return true;
                }
            }
        }
        return false;
    }
}
