package cards;

import actions.GainAttuneAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import mod.RitualistMod;
import patches.MainEnum;
import powers.AttunePower;

public class BookOfNames extends AbstractRitual {
    /*
    * Uncommon Skill
    * 2E Ritual
    * 3 Rit: Add a copy of all summons into your draw pile.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("BookOfNames");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/names.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = MainEnum.Magenta;
    private static final int MAGIC = 3;
    private static final int UPG = 1;
    private static final int COST = 1;

    // /Stat Declaration/


    public BookOfNames() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
        tags.add(MainEnum.RITUAL_CARD);
        exhaust = true;

    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //3 attune, 5 on upg
        addToBot(new GainAttuneAction(magicNumber));
        addToBot(new MakeTempCardInDiscardAction(new SummonNergal(), 1));
        addToBot(new MakeTempCardInDiscardAction(new SummonMaat(), 1));
        addToBot(new MakeTempCardInDiscardAction(new SummonRerek(), 1));
        addToBot(new MakeTempCardInDiscardAction(new SummonAgiel(), 1));




    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BookOfNames();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG);
          //  rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
