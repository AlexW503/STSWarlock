package cards;

import actions.RandomCurseToDiscardAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import mod.RitualistMod;
import patches.MainEnum;
import powers.EmptyPalmsPower;
import powers.GrowingRitesPower;

import java.util.Iterator;

public class GrowingRites extends CustomCard {
    /*
    * RARE Power
    * 1E
    * At the start of your turn gain 3 Attunement. Add a random curse to your discard pile.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("GrowingRites");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/growing.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = MainEnum.Magenta;

    private static final int COST = 1;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;


    // /Stat Declaration/


    public GrowingRites() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new GrowingRitesPower(p, magicNumber), magicNumber));
        addToBot(new RandomCurseToDiscardAction(1));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new GrowingRites();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
           // upgradeMagicNumber(UPG_MAGIC);
            isInnate = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
