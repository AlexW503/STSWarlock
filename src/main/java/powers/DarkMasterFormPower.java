package powers;


import actions.GainAttuneAction;
import actions.RandomDrawFetchAction;
import actions.RandomSummonToDeckAction;
import com.badlogic.gdx.graphics.Texture;
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

import static patches.MainEnum.RITUAL_CARD;
import static patches.MainEnum.SUMMON_CARD;

public class DarkMasterFormPower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("DarkMasterFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/darkMaster.png");
    public int count;
    public static final Logger logger = LogManager.getLogger(RitualistMod.class.getName());


    public DarkMasterFormPower(final AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        count = 1;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        img = new Texture(IMG);
        //loadRegion("demonForm");
       // source = source;

    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        count += 1;
    }


    @Override
    public void updateDescription(){
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + count + DESCRIPTIONS[2];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.hasTag(SUMMON_CARD)) {
            addToBot(new RandomSummonToDeckAction(count));
            addToBot(new GainAttuneAction(amount));
        }

    }



}
