class Type {

    // liste des noms des pokemons gen 1
    public static final String[] espece = {
        "Bulbizarre","Herbizarre","Florizarre","Salamèche","Reptincel","Dracaufeu",
        "Carapuce","Carabaffe","Tortank","Chenipan","Chrysacier","Papilusion",
        "Aspicot","Coconfort","Dardargnan","Roucool","Roucoups","Roucarnage",
        "Rattata","Rattatac","Piafabec","Rapasdepic","Abo","Arbok","Pikachu",
        "Raichu","Sabelette","Sablaireau","Nidoran♀","Nidorina","Nidoqueen",
        "Nidoran♂","Nidorino","Nidoking","Mélofée","Mélodelfe","Goupix","Feunard",
        "Rondoudou","Grodoudou","Nosferapti","Nosferalto","Mystherbe","Ortide",
        "Rafflesia","Paras","Parasect","Mimitoss","Aéromite","Taupiqueur",
        "Triopikeur","Miaouss","Persian","Psykokwak","Akwakwak","Férosinge",
        "Colossinge","Caninos","Arcanin","Ptitard","Têtarte","Tartard","Abra",
        "Kadabra","Alakazam","Machoc","Machopeur","Mackogneur","Chétiflor",
        "Boustiflor","Empiflor","Tentacool","Tentacruel","Racaillou","Gravalanch",
        "Grolem","Ponyta","Galopa","Ramoloss","Flagadoss","Magnéti","Magnéton",
        "Canarticho","Doduo","Dodrio","Otaria","Lamantine","Tadmorv","Grotadmorv",
        "Kokiyas","Crustabri","Fantominus","Spectrum","Ectoplasma","Onix",
        "Soporifik","Hypnomade","Krabby","Krabboss","Voltorbe","Électrode",
        "Noeunoeuf","Noadkoko","Osselait","Ossatueur","Kicklee","Tygnon",
        "Excelangue","Smogo","Smogogo","Rhinocorne","Rhinoféros","Leveinard",
        "Saquedeneu","Kangourex","Hypotrempe","Hypocéan","Poissirène","Poissoroy",
        "Stari","Staross","M. Mime","Insécateur","Lippoutou","Élektek","Magmar",
        "Scarabrute","Tauros","Magicarpe","Léviator","Lokhlass","Métamorph",
        "Évoli","Aquali","Voltali","Pyroli","Porygon","Amonita","Amonistar",
        "Kabuto","Kabutops","Ptéra","Ronflex","Artikodin","Électhor","Sulfura",
        "Minidraco","Draco","Dracolosse","Mewtwo","Mew"
    };

    // les types possibles
    public static final String[] nomsType = {
        "NORMAL","FEU","EAU","PLANTE","ELECTRIK","GLACE","COMBAT","POISON",
        "SOL","VOL","PSY","INSECTE","ROCHE","SPECTRE","DRAGON","SANS"
    };

    // constantes pour les types (plus facile a utiliser)
    public static final int NORMAL   = 0;
    public static final int FEU      = 1;
    public static final int EAU      = 2;
    public static final int PLANTE   = 3;
    public static final int ELECTRIK = 4;
    public static final int GLACE    = 5;
    public static final int COMBAT   = 6;
    public static final int POISON   = 7;
    public static final int SOL      = 8;
    public static final int VOL      = 9;
    public static final int PSY      = 10;
    public static final int INSECTE  = 11;
    public static final int ROCHE    = 12;
    public static final int SPECTRE  = 13;
    public static final int DRAGON   = 14;
    public static final int SANS     = 15;

    // les valeurs defficacite
    public static final double NEUTRE         = 1.0;
    public static final double INEFFICACE     = 0.0;
    public static final double PAS_EFFICACE   = 0.5;
    public static final double SUPER_EFFICACE = 2.0;

    // tableau des efficacites - ligne = attaquant, colonne = defenseur
    private static final double efficacite[][] = {
        {NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, PAS_EFFICACE, INEFFICACE, NEUTRE},
        {NEUTRE, PAS_EFFICACE, PAS_EFFICACE, SUPER_EFFICACE, NEUTRE, SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, PAS_EFFICACE, NEUTRE, PAS_EFFICACE},
        {NEUTRE, SUPER_EFFICACE, PAS_EFFICACE, PAS_EFFICACE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, NEUTRE, PAS_EFFICACE},
        {NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, PAS_EFFICACE, NEUTRE, NEUTRE, NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, PAS_EFFICACE, NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, NEUTRE, PAS_EFFICACE},
        {NEUTRE, NEUTRE, SUPER_EFFICACE, PAS_EFFICACE, PAS_EFFICACE, NEUTRE, NEUTRE, NEUTRE, INEFFICACE, SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, PAS_EFFICACE},
        {NEUTRE, NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, NEUTRE, PAS_EFFICACE, NEUTRE, NEUTRE, SUPER_EFFICACE, SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE},
        {SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, NEUTRE, PAS_EFFICACE, NEUTRE, PAS_EFFICACE, PAS_EFFICACE, PAS_EFFICACE, SUPER_EFFICACE, INEFFICACE, NEUTRE},
        {NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE, PAS_EFFICACE, PAS_EFFICACE, NEUTRE, NEUTRE, SUPER_EFFICACE, PAS_EFFICACE, PAS_EFFICACE, NEUTRE},
        {NEUTRE, SUPER_EFFICACE, NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, NEUTRE, NEUTRE, SUPER_EFFICACE, NEUTRE, INEFFICACE, NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, NEUTRE, NEUTRE},
        {NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, PAS_EFFICACE, NEUTRE, SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, PAS_EFFICACE, NEUTRE, NEUTRE},
        {NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, SUPER_EFFICACE, NEUTRE, NEUTRE, PAS_EFFICACE, NEUTRE, NEUTRE, NEUTRE, NEUTRE},
        {NEUTRE, PAS_EFFICACE, NEUTRE, SUPER_EFFICACE, NEUTRE, NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, NEUTRE, NEUTRE, PAS_EFFICACE, NEUTRE},
        {NEUTRE, SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, PAS_EFFICACE, NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, NEUTRE, SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE},
        {INEFFICACE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, INEFFICACE, NEUTRE, NEUTRE, SUPER_EFFICACE, NEUTRE},
        {NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE}
    };

    // retourne le nom de lespece selon le numero
    public static String getEspece(int num) {
        if (num >= 1 && num <= espece.length) {
            return espece[num - 1];
        }
        return "Missingno";
    }

    // retourne le nom du type en minuscule
    public static String getNomType(int type) {
        if (type >= 0 && type < nomsType.length) {
            return nomsType[type].toLowerCase();
        }
        return "inconnu";
    }

    // retourne lefficacite entre deux types
    public static double getEfficacite(int typeAtt, int typeDef) {
        if (typeAtt == SANS || typeDef == SANS) return NEUTRE;
        if (typeAtt >= 0 && typeAtt < efficacite.length &&
            typeDef >= 0 && typeDef < efficacite[typeAtt].length) {
            return efficacite[typeAtt][typeDef];
        }
        return NEUTRE;
    }

    // retourne lindice du type a partir de son nom
    public static int getIndiceType(String type) {
        if (type == null) return SANS;
        for (int i = 0; i < nomsType.length; i++) {
            if (nomsType[i].equals(type.toUpperCase())) {
                return i;
            }
        }
        return SANS;
    }
}
