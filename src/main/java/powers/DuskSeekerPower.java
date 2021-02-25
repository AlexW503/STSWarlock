package powers;


import actions.GainAttuneAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mod.RitualistMod;

public class DuskSeekerPower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("DuskSeekerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/buffTest.png");

    public DuskSeekerPower(final AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        //img = new Texture(IMG);
        loadRegion("mantra");
       // source = source;

    }
    @Override
    public void updateDescription(){
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onInitialApplication()
    {
        AbstractDungeon.player.gameHandSize -= 1;
    }

    @Override
    public void stackPower(int stackAmount) {
        amount += stackAmount;
        AbstractDungeon.player.gameHandSize -= stackAmount;
    }


    @Override
    public void atStartOfTurnPostDraw() {
        if(AbstractDungeon.player.drawPile.size() == 0)
            addToBot(new EmptyDeckShuffleAction());
        addToBot(new SeekAction(this.amount));


    }



}
