import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Pokemon {

    // les infos du pokemon
    private int numPokedex;
    private String nom;
    private int type1;
    private int type2; // si ya pas de 2eme type on met SANS
    private int pv;
    private int pvMax; // pour garder le max
    private int att;
    private int def;
    private int vit;

    // constructeur vide (je sais pas trop si on en a besoin mais bon)
    public Pokemon() {
        this.numPokedex = 0;
        this.nom = "Missingno";
        this.type1 = Type.NORMAL;
        this.type2 = Type.SANS;
        this.pv = 33;
        this.pvMax = 33;
        this.att = 136;
        this.def = 0;
        this.vit = 29;
    }

    // constructeur avec tous les parametres
    public Pokemon(int numPokedex, String nom, int type1, int type2, int pv, int att, int def, int vit) {
        this.numPokedex = numPokedex;
        this.nom = nom;
        this.type1 = type1;
        this.type2 = type2;
        this.pv = pv;
        this.pvMax = pv; // pvMax = pv au debut
        this.att = att;
        this.def = def;
        this.vit = vit;
    }

    // constructeur qui lit le csv directement
    public Pokemon(int numPokedex, String nom) {
        this.numPokedex = numPokedex;
        this.nom = nom;
        this.type1 = Type.SANS;
        this.type2 = Type.SANS;
        this.pv = 1;
        this.pvMax = 1;
        this.att = 0;
        this.def = 0;
        this.vit = 0;

        // on cherche le pokemon dans le fichier csv
        String[] chemins = {
            "pokedex_gen1.csv",
            "../TP_base/pokedex_gen1.csv",
            "TP_base/pokedex_gen1.csv"
        };

        boolean trouve = false;
        for (String chemin : chemins) {
            try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
                String ligne;
                br.readLine(); // skip la premiere ligne (header)
                while ((ligne = br.readLine()) != null) {
                    String[] tab = ligne.split(";");
                    if (tab.length >= 8 && Integer.parseInt(tab[0].trim()) == numPokedex) {
                        this.type1 = Type.getIndiceType(tab[2].trim());
                        // si type2 est vide on met SANS
                        this.type2 = (tab[3].trim().isEmpty()) ? Type.SANS : Type.getIndiceType(tab[3].trim());
                        this.pv = Integer.parseInt(tab[4].trim());
                        this.pvMax = this.pv;
                        this.att = Integer.parseInt(tab[5].trim());
                        this.def = Integer.parseInt(tab[6].trim());
                        this.vit = Integer.parseInt(tab[7].trim());
                        trouve = true;
                        break;
                    }
                }
                if (trouve) break;
            } catch (IOException | NumberFormatException e) {
                // essaye le chemin suivant
            }
        }

        if (!trouve) {
            System.err.println("pokemon #" + numPokedex + " pas trouve dans le csv");
        }
    }

    // getters
    public int getNumPokedex() { return numPokedex; }
    public String getNom() { return nom; }
    public int getType1() { return type1; }
    public int getType2() { return type2; }
    public int getPv() { return pv; }
    public int getPvMax() { return pvMax; }
    public int getAtt() { return att; }
    public int getDef() { return def; }
    public int getVit() { return vit; }

    // setters
    public void setNom(String nom) { this.nom = nom; }
    public void setPv(int pv) {
        // les pv peuvent pas etre negatifs
        if (pv < 0) pv = 0;
        this.pv = pv;
    }

    // retourne true si le pokemon est vivant
    public boolean estVivant() {
        return pv > 0;
    }

    public String getEspece() {
        return Type.getEspece(numPokedex);
    }

    // calcule combien de degats on fait selon les types
    private double getMultiplicateur(Pokemon def) {
        double mult = Type.getEfficacite(this.type1, def.type1);
        if (def.type2 != Type.SANS) {
            mult = mult * Type.getEfficacite(this.type1, def.type2);
        }
        // si on a un 2eme type on prend le meilleur
        if (this.type2 != Type.SANS) {
            double mult2 = Type.getEfficacite(this.type2, def.type1);
            if (def.type2 != Type.SANS) {
                mult2 = mult2 * Type.getEfficacite(this.type2, def.type2);
            }
            if (mult2 > mult) mult = mult2;
        }
        return mult;
    }

    // methode attaque - le plus rapide attaque en premier
    public void attaque(Pokemon adversaire) {
        Pokemon p1, p2;

        // on regarde qui est le plus rapide
        if (this.vit >= adversaire.vit) {
            p1 = this;
            p2 = adversaire;
        } else {
            p1 = adversaire;
            p2 = this;
        }

        faireAttaque(p1, p2);

        // le 2eme attaque seulement sil est encore vivant
        if (p2.estVivant()) {
            faireAttaque(p2, p1);
        }
    }

    // fait une attaque et calcule les degats
    private static void faireAttaque(Pokemon att, Pokemon def) {
        double mult = att.getMultiplicateur(def);
        int degats = (int)((att.att - def.def) * mult);

        // degats minimum = 1
        if (degats < 1) degats = 1;

        def.setPv(def.pv - degats);

        // message selon lefficacite
        String msg = "";
        if (mult == 0.0) msg = " C'est sans effet !";
        else if (mult < 1.0) msg = " Ce n'est pas tres efficace...";
        else if (mult > 1.0) msg = " C'est super efficace !";

        System.out.println(att.nom + " attaque " + def.nom + " !" + msg);
        System.out.println("  degats: " + degats + " | pv de " + def.nom + ": " + def.pv + "/" + def.pvMax);

        if (!def.estVivant()) {
            System.out.println("  " + def.nom + " est KO !");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Pokemon)) return false;
        Pokemon other = (Pokemon) obj;
        return this.numPokedex == other.numPokedex && this.nom.equals(other.nom);
    }

    @Override
    public String toString() {
        String t2 = (type2 == Type.SANS) ? "aucun" : Type.getNomType(type2);
        return "Numero : " + numPokedex + "\n"
             + "Espece : " + Type.getEspece(numPokedex) + "\n"
             + "Nom    : " + nom + "\n"
             + "Type1  : " + Type.getNomType(type1) + "\n"
             + "Type2  : " + t2 + "\n"
             + "PV     : " + pv + "/" + pvMax + "\n"
             + "Att    : " + att + "\n"
             + "Def    : " + def + "\n"
             + "Vit    : " + vit;
    }
}
