package powers;


import actions.GainAttuneAction;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mod.RitualistMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeepReservesPower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("DeepReservesPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/buffTest.png");
    public static final Logger logger = LogManager.getLogger(RitualistMod.class.getName());

    private int draw;

    public DeepReservesPower(final AbstractCreature owner, int amount, int draw) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.draw = draw;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        //img = new Texture(IMG);
        loadRegion("darkembrace");
       // source = source;

    }

    @Override
    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        this.draw += 2;
        BaseMod.MAX_HAND_SIZE += stackAmount;
        logger.info("Max hand size stack increase: ");
        logger.info(BaseMod.MAX_HAND_SIZE);
        updateDescription();

    }


    @Override
    public void onInitialApplication() {
        logger.info(amount);
       BaseMod.MAX_HAND_SIZE += amount;
       logger.info("Max hand size post increase: ");
       logger.info(BaseMod.MAX_HAND_SIZE);
       logger.info(BaseMod.MAX_HAND_SIZE);

    }

    @Override
    public void updateDescription(){
        description = DESCRIPTIONS[0] + draw + DESCRIPTIONS[1]+ amount;
    }

    @Override
    public void atStartOfTurn() {

        addToBot(new DrawCardAction(AbstractDungeon.player, draw));
        //this.p.hand.refreshHandLayout();
       // addToBot(new GainAttuneAction(amount));

    }

    @Override
    public void onVictory() {
       BaseMod.MAX_HAND_SIZE -= amount;
      logger.info("Max hand size post decrease: ");
      logger.info(BaseMod.MAX_HAND_SIZE);
    }
}
