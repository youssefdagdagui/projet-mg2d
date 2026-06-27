// une case du plateau - peut contenir un pokemon ou etre vide

public class Case {

    private Pokemon pokemon; // null si la case est vide
    private int joueur; // 1 = rouge, 2 = vert, 0 = vide
    private boolean mouvPossible; // true = cercle blanc affiche

    public Case() {
        this.pokemon = null;
        this.joueur = 0;
        this.mouvPossible = false;
    }

    public Pokemon getPokemon() { return pokemon; }
    public int getJoueur() { return joueur; }
    public boolean isMouvementPossible() { return mouvPossible; }

    // verifier si la case est vide
    public boolean estVide() { return pokemon == null; }

    public void setPokemon(Pokemon p, int joueur) {
        this.pokemon = p;
        this.joueur = joueur;
    }

    // vider la case quand le pokemon se deplace ou est ko
    public void vider() {
        this.pokemon = null;
        this.joueur = 0;
    }

    public void setMouvementPossible(boolean b) {
        this.mouvPossible = b;
    }
}
