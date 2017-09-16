
public class TypeNormal extends CardName {
    public float hardness;
    public float gravScoreValue;
    public String cleavage;
    public int cleavageValue;
    public String crustalAbundance;
    public int crustalAbundanceValue;
    public String ecoValue;
    public int ecoValueValue;

    TypeNormal(String name, float hardScore, float gravScore, String cleavScore, String abuScore, String ecoVScore)
    {
        super(name);
        hardness = hardScore;
        gravScoreValue = gravScore;
        cleavage = cleavScore;
        crustalAbundance = abuScore;
        ecoValue = ecoVScore;
        cleavageValue = tocleaveValue();
        crustalAbundanceValue = toaValue();
        ecoValueValue = toeValue();
    }

// getter and setter methods
    public void setHardness(float hardness) {
        this.hardness = hardness;
    }

    public void setGravScoreValue(float gravScoreValue) {
        this.gravScoreValue = gravScoreValue;
    }

    public void setCleavage(String cleavage) {
        this.cleavage = cleavage;
    }

    public void setCleavageValue(int cleavageValue) {
        this.cleavageValue = cleavageValue;
    }

    public void setCrustalAbundance(String crustalAbundance) {
        this.crustalAbundance = crustalAbundance;
    }

    public void setCrustalAbundanceValue(int crustalAbundanceValue) {
        this.crustalAbundanceValue = crustalAbundanceValue;
    }

    public void setEcoValue(String ecoValue) {
        this.ecoValue = ecoValue;
    }

    public void setEcoValueValue(int ecoValueValue) {
        this.ecoValueValue = ecoValueValue;
    }

    public int getCleavageValue() {
        return cleavageValue;
    }

    public int getCrustalAbundanceValue() {
        return crustalAbundanceValue;
    }

    public int getEcoValueValue() {
        return ecoValueValue;
    }

    public float getHardness() {
        return hardness;
    }

    public float getGravScoreValue() {
        return gravScoreValue;
    }

    public String getCleavage() {
        return cleavage;
    }

    public String getCrustalAbundance() {
        return crustalAbundance;
    }

    public String getEcoValue() {
        return ecoValue;
    }
// determining normal cards attributes value for compasrison
    public int toaValue()
    {
        int aValue = 0;
        String x = getCrustalAbundance();
        if(x.equals("ultratrace"))
        {aValue=1;}
        else if(x.equals("trace"))
        {aValue=2;}
        else if(x.equals("low"))
        {aValue=3;}
        else if(x.equals("moderate"))
        {aValue=4;}
        else if(x.equals("high"))
        {aValue=5;}
        else if(x.equals("very high"))
        {aValue=6;}
        return aValue;
    }
    public int toeValue()
    {
        int eValue = 0;
        String x = getEcoValue();
        if(x.equals("trivial"))
        {eValue=1;}
        else if(x.equals("low"))
        {eValue=2;}
        else if(x.equals("moderate"))
        {eValue=3;}
        else if(x.equals("high"))
        {eValue=4;}
        else if(x.equals("very high"))
        {eValue=5;}
        else if(x.equals("I'm rich!"))
        {eValue=6;}
        return eValue;
    }

    public int tocleaveValue()
    {
        int cleaValue = 0;
        String x = getCleavage();
        if(x.equals("none"))
        {cleaValue = 1;}
        else if(x.equals("poor/none"))
        {cleaValue = 2;}
        else if(x.equals("1 poor"))
        {cleaValue = 3;}
        else if(x.equals("2 poor"))
        {cleaValue = 4;}
        else if(x.equals("1 good"))
        {cleaValue = 5;}
        else if(x.equals("1 good/1 poor"))
        {cleaValue = 6;}
        else if(x.equals("2 good"))
        {cleaValue = 7;}
        else if(x.equals("3 good"))
        {cleaValue = 8;}
        else if(x.equals("1 perfect"))
        {cleaValue = 9;}
        else if(x.equals("1 perfect/1 good"))
        {cleaValue = 10;}
        else if(x.equals("1 perfect/2 good"))
        {cleaValue = 11;}
        else if(x.equals("2 perfect/1 good"))
        {cleaValue = 12;}
        else if(x.equals("3 perfect"))
        {cleaValue = 13;}
        else if(x.equals("4 perfect"))
        {cleaValue = 14;}
        else if(x.equals("6 perfect"))
        {cleaValue = 15;}
        return cleaValue;
    }

}
