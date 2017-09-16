public class TypeSuperTrump extends CardName {
// getting name
    TypeSuperTrump(String name)
    {
        super(name);
    }
// determining supertrump cards effect and its descriptions based on their name

    public String cardeffect()
    {
        String effect = "";
        String x = getName();
        if(x.equals("The Mineralogist"))
        {effect = "C";}
        else if(x.equals("The Geologist"))
        {effect = "NEW";}
        else if(x.equals("The Geophysicist"))
        {effect = "S";}
        else if(x.equals("The Petrologist"))
        {effect = "A";}
        else if(x.equals("The Miner"))
        {effect = "E";}
        else if(x.equals("The Gemmologist"))
        {effect = "H";}
        return effect;
    }

    public String cardDescription()
    {
        String description = "";
        String x = getName();
        if(x.equals("The Mineralogist"))
        {description = "Change trump category to cleavage";}
        else if(x.equals("The Geologist"))
        {description = "Change to trump category of your choice";}
        else if(x.equals("The Geophysicist"))
        {description = "Change trump category to specific gravity or throw magnetite";}
        else if(x.equals("The Petrologist"))
        {description = "Change trump category to crustal abundance";}
        else if(x.equals("The Miner"))
        {description = "Change trump category to economic value";}
        else if(x.equals("The Gemmologist"))
        {description = "Change trump category to hardness";}
        return description;
    }
}
