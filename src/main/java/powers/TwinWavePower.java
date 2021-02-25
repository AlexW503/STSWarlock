package powers;


import actions.GainAttuneAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mod.RitualistMod;

public class TwinWavePower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("TwinWavePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/buffTest.png");
    int[] multiDamage;
    int damageToDisplay;

    public TwinWavePower(AbstractCreature owner, int damage) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = 1;
       // multiDamage = damage;
        damageToDisplay = damage;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = true;
        //img = new Texture(IMG);
        loadRegion("combust");
       // source = source;

    }
    @Override
    public void updateDescription(){
        description = DESCRIPTIONS[0] + damageToDisplay + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() { //Remove this power and add Twisted Ecstasy
        for(int i = amount; i >0; i-- )
            addToBot(new DamageAllEnemiesAction(owner,  DamageInfo.createDamageMatrix(damageToDisplay, true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));

        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));

    }



}
