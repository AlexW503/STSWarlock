package powers;


import actions.ExtraditionAction;
import actions.GainAttuneAction;
import characters.Ritualist;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPoisonOnRandomMonsterAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import mod.RitualistMod;

public class ExtraditionPower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("ExtraditionPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/buffTest.png");





    public ExtraditionPower(final AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount; // displayed amount is total amount of bomb damage
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        //img = new Texture(IMG);
        this.loadRegion("accuracy");
       // source = source;

    }
    @Override
    public void updateDescription(){

        if(amount <= 1)
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        else
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];

    }

    @Override
    public void onDeath() {
        for(int i = 0; i < amount; i++) {
            if (owner.hasPower(PossessionPower.POWER_ID)) {
                int pos = owner.getPower(PossessionPower.POWER_ID).amount;
                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    this.flash();
                    addToBot(new ExtraditionAction(AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng), pos));

                } else {
                    RitualistMod.logger.info("no target for the extradition");
                }
            }
        }
    }
/*
    @Override
    public void atEndOfRound() {
        amount--;
        if(amount <= 0)
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

*/
}
