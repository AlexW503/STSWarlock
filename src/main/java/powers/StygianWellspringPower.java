package powers;


import cards.UN_SoulWard;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mod.RitualistMod;

public class StygianWellspringPower extends AbstractPower implements NonStackablePower {

    public static final String POWER_ID = RitualistMod.makeID("StygianWellspringPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = RitualistMod.makePath("customImages/buffTest.png");
    public boolean isUpgraded = false;

    public StygianWellspringPower(final AbstractCreature owner, int amount, boolean upgrade) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = true;
        isUpgraded = upgrade;
        //img = new Texture(IMG);
        loadRegion("juggernaut");
       // source = source;

    }
    @Override
    public void updateDescription(){
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        flash();
        AbstractCard card = AbstractDungeon.getCard(AbstractDungeon.rollRarity(), AbstractDungeon.cardRandomRng).makeCopy();
        card.setCostForTurn(0);
        addToBot(new MakeTempCardInHandAction(card, 1, false));
        amount--;
        updateDescription();
        if(amount <= 0)
            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));



    }



}
