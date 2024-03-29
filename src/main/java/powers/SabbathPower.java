package powers;


import actions.GainAttuneAction;
import actions.RandomDrawFetchAction;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mod.RitualistMod;

import static patches.MainEnum.RITUAL_CARD;

public class SabbathPower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("SabbathPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/sabbath.png");

    public SabbathPower(final AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        img = new Texture(IMG);
        //loadRegion("cExplosion");
       // source = source;

    }
    @Override
    public void updateDescription(){
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    /*
    @Override
    public void onInitialApplication() {
        BaseMod.MAX_HAND_SIZE += 3;
    }
    */
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.hasTag(RITUAL_CARD))
            // addToBot(new RandomDrawFetchAction(amount));
            addToBot(new GainAttuneAction(amount));
    }

    /*
    @Override
    public void atStartOfTurn() {
        addToBot(new GainAttuneAction(amount));

    }
    */


}
