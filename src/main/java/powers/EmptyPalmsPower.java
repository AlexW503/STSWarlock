package powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mod.RitualistMod;


public class EmptyPalmsPower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("EmptyPalmsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/buffTest.png");
    public static boolean check = false; //has the hand been empty this turn

    public EmptyPalmsPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        //img = new Texture(IMG);
        loadRegion("nightmare");
        // source = source;
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() { // At turn start reset proc check
        check = false;
    }

    @Override
   public void onDrawOrDiscard(){
        if(AbstractDungeon.player.hand.size() <= 0 && (check == false))
        {
            addToBot(new com.megacrit.cardcrawl.actions.common.GainBlockAction(owner, owner, amount));
            check = true;
        }
    }
    @Override
    public void onExhaust(AbstractCard card) {
        if(AbstractDungeon.player.hand.size() <= 0 && (check == false))
        {
            addToBot(new com.megacrit.cardcrawl.actions.common.GainBlockAction(owner, owner, amount));
            check = true;
        }
    }
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) { //Could also use onAfterUse if bloodletter synergy is OP
        if(AbstractDungeon.player.hand.size() <= 1 && (check == false))
        {
            addToBot(new com.megacrit.cardcrawl.actions.common.GainBlockAction(owner, owner, amount));
            check = true;
        }
    }
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        check = true;
    }


}
