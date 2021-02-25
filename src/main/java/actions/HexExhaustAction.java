package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import powers.PossessionPower;

public class HexExhaustAction extends AbstractGameAction
{
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String TEXT = "Select a card to exhaust.";
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean anyNumber;
    private boolean canPickZero;
    public static int numExhausted;
    private AbstractMonster mo;
    private int damage = 0;

    public HexExhaustAction(int amount, boolean isRandom, boolean anyNumber, boolean canPickZero)
    {
        this.anyNumber = anyNumber;
        this.p = AbstractDungeon.player;
        this.canPickZero = canPickZero;
        this.isRandom = isRandom;
        this.amount = amount;
        this.duration = (this.startDuration = Settings.ACTION_DUR_FAST);
        this.actionType = AbstractGameAction.ActionType.EXHAUST;
    }



    public HexExhaustAction(AbstractCreature target, AbstractMonster enemy, AbstractCreature source, int dmg, int amount, boolean isRandom)
    {
        this(amount, isRandom, false, false);
        this.target = target;
        this.source = source;
        this.mo = enemy;
        this.damage = dmg;
    }

    /*
        public HexExhaustAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, boolean anyNumber)
    {
        this(amount, isRandom, anyNumber);
        this.target = target;
        this.source = source;
    }
    public HexExhaustAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, boolean anyNumber, boolean canPickZero)
    {
        this(amount, isRandom, anyNumber, canPickZero);
        this.target = target;
        this.source = source;
    }

    public HexExhaustAction(boolean isRandom, boolean anyNumber, boolean canPickZero)
    {
        this(99, isRandom, anyNumber, canPickZero);
    }

    public HexExhaustAction(int amount, boolean canPickZero)
    {
        this(amount, false, false, canPickZero);
    }


    public HexExhaustAction(int amount, boolean isRandom, boolean anyNumber, boolean canPickZero, float duration)
    {
        this(amount, isRandom, anyNumber, canPickZero);
        this.duration = (this.startDuration = duration);
    }

    public HexExhaustAction(int amount, boolean isRandom, boolean anyNumber)
    {
        this(amount, isRandom, anyNumber, false);
    }

     */

    public void update()
    {
        int i;
        if (this.duration == this.startDuration)
        {
            if (this.p.hand.size() == 0)
            {
                this.isDone = true;
                return;
            }
            if ((!this.anyNumber) && (this.p.hand.size() <= this.amount))
            {
                this.amount = this.p.hand.size();
                numExhausted = this.amount;
                int tmp = this.p.hand.size();
                for (i = 0; i < tmp; i++)
                {
                    AbstractCard c = this.p.hand.getTopCard();
                    this.p.hand.moveToExhaustPile(c);
                    if(c.type == AbstractCard.CardType.CURSE)
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, p, new PossessionPower(mo, p, damage), damage));

                }

                return;
            }
            else
            {
                numExhausted = this.amount;
                AbstractDungeon.handCardSelectScreen.open(TEXT, this.amount, this.anyNumber, this.canPickZero);
                tickDuration();
                return;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                this.p.hand.moveToExhaustPile(c);
                if(c.type == AbstractCard.CardType.CURSE)
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, p, new PossessionPower(mo, p, damage), damage));

            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        tickDuration();
    }
}
