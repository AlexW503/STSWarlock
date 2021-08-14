package powers;


import actions.GainAttuneAction;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import mod.RitualistMod;

public class BloodyVigorPower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("BloodyVigorPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/bloodyPower.png");
    public static int discard;

    public BloodyVigorPower(final AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        discard = 1;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        img = new Texture(IMG);
        //loadRegion("mayhem");
       // source = source;

    }
    @Override
    public void updateDescription(){
        description = DESCRIPTIONS[0] + discard + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public void stackPower(int stackAmount){
        discard++;
        amount+=stackAmount;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        addToBot(new DiscardAction(owner, owner, discard, false));
        addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
     //   addToBot(new ApplyPowerAction(owner, owner, new DexterityPower(owner, amount), amount));


    }



}
