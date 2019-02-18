package powers;


import actions.GainAttuneAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mod.RitualistMod;

public class DeepReservesPower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("DeepReservesPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/buffTest.png");
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
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        this.draw += 2;

    }

    @Override
    public void updateDescription(){
        description = DESCRIPTIONS[0] + draw + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public void atStartOfTurn() {

        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, draw));
        //this.p.hand.refreshHandLayout();
        AbstractDungeon.actionManager.addToBottom(new GainAttuneAction(amount));

    }



}
