package cards;


import actions.SetPowerZeroAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.RitualistMod;
import patches.MainEnum;
import orbs.BaphOrb;
import powers.AttunePower;

public class SummonBaphomet extends CustomCard {
    /*
    * Rare Skill
    * 3E Summon
    * Play the top ATT/2 cards of your deck. VESSEL: Lose 1E.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("SummonBaphomet");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/summon.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = MainEnum.PURPLE;

    private static final int COST = 4;
    private static final int DIV = 1;
    private static final int UPG = 3;
    private static final int BASE = 2;
    private int ATT = 0; //player's attune
    private boolean exhaustCards = false;

    // /Stat Declaration/


    public SummonBaphomet() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BASE;
        magicNumber = baseMagicNumber;
        exhaust = true;
        tags.add(MainEnum.SUMMON_CARD);
    }
    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(AttunePower.POWER_ID)) {
            ATT = AbstractDungeon.player.getPower(AttunePower.POWER_ID).amount;
            baseMagicNumber = BASE + ATT/DIV;
            if(upgraded)
                baseMagicNumber += UPG;

            magicNumber = baseMagicNumber;

        }
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard card;
        for(int i = magicNumber; i > 0; i--) {
            card = p.drawPile.getTopCard();
            if(card.hasTag(MainEnum.SUMMON_CARD)){
                p.drawPile.removeCard(card);
                p.discardPile.addToRandomSpot(card);
                i++;
            }
            else
                AbstractDungeon.actionManager.addToBottom(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), exhaustCards));
        }

        AbstractDungeon.actionManager.addToBottom(new SetPowerZeroAction(p, p, AttunePower.POWER_ID));
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new BaphOrb()));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new SummonBaphomet();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            exhaustCards = false;
        }
    }

}
