package powers;


import actions.GainAttuneAction;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import mod.RitualistMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class VendettaPower extends AbstractPower {

    public static final String POWER_ID = RitualistMod.makeID("VendettaPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/buffTest.png");
    public static final Logger logger = LogManager.getLogger(RitualistMod.class.getName());

    private static final int maxCount = 3;
    private int count = 0;
    private int attune = 0;

    public VendettaPower(final AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        attune = 2;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        img = new Texture(IMG);
        //loadRegion("darkembrace");
       // source = source;

    }

    @Override
    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        attune += 2;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            ++count;
            if (count == 3) {
                count = 0;
                this.flash();
                //apply possession to all enemies
                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    this.flash();
                    Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();

                    while(var1.hasNext()) {
                        AbstractMonster m = (AbstractMonster)var1.next();
                        if (!m.isDead && !m.isDying) {
                            addToBot(new ApplyPowerAction(m, this.owner, new PossessionPower(m, this.owner, this.amount), this.amount));
                        }
                    }
                }
                addToBot(new GainAttuneAction(attune));
            }
        }
        updateDescription();

    }

    @Override
    public void updateDescription(){
        description = DESCRIPTIONS[0] + (maxCount-count) + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + attune + DESCRIPTIONS[3];
    }

    @Override
    public void atStartOfTurn() {
        amount = 0;
    }

    @Override
    public void onVictory() {
        BaseMod.MAX_HAND_SIZE -= amount;
        logger.info("Max hand size post decrease: ");
        logger.info(BaseMod.MAX_HAND_SIZE);
    }
}
