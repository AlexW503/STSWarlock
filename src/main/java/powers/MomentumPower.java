package powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;
import mod.RitualistMod;

public class MomentumPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = RitualistMod.makeID("MomentumPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/buffTest.png");
    public int energy = 1;
    public int maxPlay = 8;

    public MomentumPower(final AbstractCreature owner, int max) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = 1;
        this.maxPlay = max;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
       // img = new Texture(IMG);
        loadRegion("echo");
       // source = source;

    }

    @Override
    public void stackPower(int stackAmount){
        energy++; //instead of gaining amount, just make it give one more energy on proc

    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + (maxPlay-amount) + DESCRIPTIONS[1] + energy + DESCRIPTIONS[2];
    }

   // @Override
  //  public void atStartOfTurn() {amount = 0;}

   @Override
    public void onUseCard(AbstractCard card, UseCardAction action){
        amount++; //stacks shown on power, go up on card play
        updateDescription();
        if(amount >= maxPlay){ //once it reaches the max set back to 0 and gain energy
            addToBot(new GainEnergyAction(energy));
            amount = 0;

        }
   }

}
