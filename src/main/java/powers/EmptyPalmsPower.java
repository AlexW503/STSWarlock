package powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mod.RitualistMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EmptyPalmsPower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("EmptyPalmsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/emptyPalms.png");
    public static final Logger logger = LogManager.getLogger(RitualistMod.class.getName());

    public static boolean check = false; //has the hand been empty this turn

    public EmptyPalmsPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        check = false;
        type = PowerType.BUFF;
        isTurnBased = false;
        img = new Texture(IMG);
       // loadRegion("hello");
        // source = source;
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() { // At turn start reset proc check
        check = false;
        logger.info("Turn Start: Check is " + check + " Hand Size is " + AbstractDungeon.player.hand.size());
    }

    @Override
   public void onDrawOrDiscard(){
        if(AbstractDungeon.player.hand.size() == 0 && (check == false))
        {
            addToBot(new GainBlockAction(owner, owner, amount));
            check = true;
            logger.info("Draw/Discard Gave " + amount + " Block and Set Check");
        }
        logger.info("OnDraw/Discard: Check is " + check + " Hand Size is " + AbstractDungeon.player.hand.size());

    }
    @Override
    public void onExhaust(AbstractCard card) {
        if(AbstractDungeon.player.hand.size() == 0 && (check == false))
        {
            addToBot(new GainBlockAction(owner, owner, amount));
            check = true;
            logger.info("Exhaust Gave " + amount + " Block and Set Check");

        }
        logger.info("On Exhaust: Check is " + check + " Hand Size is " + AbstractDungeon.player.hand.size());

    }
    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) { //Could also use onAfterUse if bloodletter synergy is OP
        if(AbstractDungeon.player.hand.size() == 0 && (check == false))
        {
            addToBot(new GainBlockAction(owner, owner, amount));
            check = true;
            logger.info("AfterCard Gave " + amount + " Block and Set Check");

        }
        logger.info("After Card: Check is " + check + " Hand Size is " + AbstractDungeon.player.hand.size());

    }


    @Override
    public void atEndOfTurn(boolean isPlayer) {
          check = true;

    }


}
