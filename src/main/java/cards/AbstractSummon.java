package cards;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import patches.MainEnum;
import powers.DarkMasterFormPower;


public abstract class AbstractSummon extends CustomCard {
    public AbstractSummon(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img,  cost, rawDescription, type, color, rarity, target);
    }
    public boolean isReduced = false;




    @Override
    public void applyPowers() {
        super.applyPowers();
        if(AbstractDungeon.player.hasPower(DarkMasterFormPower.POWER_ID) && costForTurn != 0 && !isReduced) {
           // cost -= 1;
            modifyCostForCombat(-1);
            isReduced = true;
        }
    }



}
