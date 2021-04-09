package cards;

import actions.GainAttuneAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.RitualistMod;
import patches.MainEnum;

public class FranticSearch extends AbstractRitual {
    /*
    * UnCommon Skill
    * 2E Ritual
    * Ritual  Gain 2E, Draw 2 cards, discard 2
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("FranticSearch");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/frantic.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = MainEnum.Magenta;

    private static final int COST = 2;
    private static final int DRAW = 2;
    private static final int ENERGY = 2;
    private static final int UPG = 1;

    // /Stat Declaration/


    public FranticSearch() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = DRAW;
        magicNumber = baseMagicNumber;
        tags.add(MainEnum.RITUAL_CARD);

    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainAttuneAction(1));
        addToBot(new DrawCardAction(p, magicNumber));
        addToBot(new GainEnergyAction(ENERGY));
        addToBot(new DiscardAction(p, p, 2, false));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new FranticSearch();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG);
        }
    }

}
