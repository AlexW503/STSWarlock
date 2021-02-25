package cards;

import actions.GainAttuneAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.RitualistMod;
import patches.MainEnum;
import powers.DeliriumWillPower;

public class DeliriumWill extends AbstractRitual {
    /*
    * UNC Skill
    * 3E
    * Add 3 Soul Wards to your hand. At the start of your next !M! turns, add a Soul Ward to your hand.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("DeliriumWill");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/delirium.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = MainEnum.Magenta;

    private static final int COST = 3;
    private static final int TO_HAND = 2;
    private static final int TURNS = 2;
    private static final int UPG = 1;
    AbstractCard CARD_TO_MAKE = new UN_SoulWard();

    // /Stat Declaration/


    public DeliriumWill() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = TURNS;
        magicNumber = baseMagicNumber;
        cardsToPreview = CARD_TO_MAKE;
        tags.add(MainEnum.RITUAL_CARD);


    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
       // addToBot(new MakeTempCardInHandAction(new UN_SoulWard(), TO_HAND));
        addToBot(new GainAttuneAction(3));

        addToBot(new MakeTempCardInHandAction(CARD_TO_MAKE, TO_HAND));

        if(!upgraded)
            addToBot(new ApplyPowerAction(p, p, new DeliriumWillPower(p, magicNumber, false), magicNumber));
        else
            addToBot(new ApplyPowerAction(p, p, new DeliriumWillPower(p, magicNumber, true), magicNumber));


    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DeliriumWill();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
         //   upgradeMagicNumber(UPG);
            CARD_TO_MAKE.upgrade();
            rawDescription = UPGRADE_DESCRIPTION;

            initializeDescription();
        }
    }

}
