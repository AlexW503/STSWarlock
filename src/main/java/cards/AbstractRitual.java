package cards;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import powers.TwistedPower;


public abstract class AbstractRitual extends CustomCard {
    public AbstractRitual(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img,  cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public void applyPowers() {
        if(AbstractDungeon.player.hasPower(TwistedPower.POWER_ID) && costForTurn != 0)
            costForTurn = 0;

    }


}
