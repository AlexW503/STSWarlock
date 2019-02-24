package powers;


import actions.GainAttuneAction;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.unique.RipAndTearAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mod.RitualistMod;
import patches.MainEnum;

public class SymbioteFormPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = RitualistMod.makeID("SymbioteFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/buffPower.png");
    int att = 0;

    public SymbioteFormPower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
       // img = new Texture(IMG);
        loadRegion("demonForm");


    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

   @Override
    public void onUseCard(AbstractCard card, UseCardAction action){
        if(card.hasTag(MainEnum.SUMMON_CARD)) { //deal damage to number of enemies based on power stack
            action.exhaustCard = false;
            if (owner.hasPower(AttunePower.POWER_ID)) {
                att = owner.getPower(AttunePower.POWER_ID).amount;
                att /= 2;
            }

        }
   }

   @Override
   public void onAfterCardPlayed(AbstractCard usedCard) {
       if(usedCard.hasTag(MainEnum.SUMMON_CARD))
           AbstractDungeon.actionManager.addToBottom(new GainAttuneAction(att));

        att = 0;
   }

}
