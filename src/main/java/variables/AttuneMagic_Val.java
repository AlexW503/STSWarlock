package variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import powers.AttunePower;


public class AttuneMagic_Val extends DynamicVariable{
    // Custom Dynamic Variables are what you do if you need your card text to display a
    // cool, changing number

    // This is what you type in your card string to make the variable show up.
    // Remember to encase it in "!"'s in the json!

    public int GetAttune()
    {
        if (AbstractDungeon.player != null)
        {
            if (AbstractDungeon.player.hasPower(AttunePower.POWER_ID))
                return AbstractDungeon.player.getPower(AttunePower.POWER_ID).amount;
       }
       return 0;
    }

    @Override
    public String key()
    {
        return "ATT_M";
    }

    // Checks whether the current value is different than the base one.
    // For example, this will check whether your damage is modified (i.e. by strength) and color the variable appropriately (Green/Red).
    @Override
    public boolean isModified(AbstractCard card)
    {
        return card.isDamageModified;
    }

    // The value the variable should display.
    // In our case, it displays the damage the card would do, multiplied by the amount of energy the player currently has.
    @Override
    public int value(AbstractCard card)
    {
        return (card.damage + (GetAttune()*card.magicNumber));
    }

    // The baseValue the variable should display.
    // just like baseBlock or baseDamage, this is what the variable should reset to by default. (the base value before any modifications)
    @Override
    public int baseValue(AbstractCard card)
    {
       return (card.damage + GetAttune()*card.magicNumber);
    }

    // If the card has it's damage upgraded, this variable will glow green on the upgrade selection screen as well.
    @Override
    public boolean upgraded(AbstractCard card)
    {
        return card.upgradedDamage;
    }
}
