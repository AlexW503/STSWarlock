package cards;

import actions.SetPowerZeroAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import mod.RitualistMod;
import orbs.BaphOrb;
import orbs.MaatOrb;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import patches.MainEnum;
import powers.AttunePower;
import powers.MaatPower;
import powers.MaatThornPower;
import variables.MagicPlus2;

public class SummonMaat extends AbstractSummon {
    /*
    * UNC  Skill
    * 2E Summon
    * Gain ATT block at the end of the next 3 turns.  Summon: Gain ATT/4 thorns. Banishes after 3 turns.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("SummonMaat");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/maat.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final Logger logger = LogManager.getLogger(RitualistMod.class.getName());


    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = MainEnum.Magenta;

    private static final int COST = 2;
    private static final int TURNS = 3;
    private static final int DIV = 4;
    private static final int UPG = 2;
    private static final int BASE = 5;
    private int ATT = 1; //player's attune
    // /Stat Declaration/

    public SummonMaat() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BASE;
        baseMagicNumber = 1; //gonna be att/div
        magicNumber = baseMagicNumber;
        exhaust = true;
        tags.add(MainEnum.SUMMON_CARD);

    }
    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(AttunePower.POWER_ID)) {
            ATT = AbstractDungeon.player.getPower(AttunePower.POWER_ID).amount;
            baseBlock = BASE + ATT;
            if(upgraded)
               baseBlock += UPG;
            baseMagicNumber = 1+(ATT/DIV);


        }
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        magicNumber = baseMagicNumber;
       // logger.info(magicNumber);

        addToBot(new ApplyPowerAction(p, p, new MaatPower(p, block, TURNS), block));
        addToBot(new ApplyPowerAction(p, p, new MaatThornPower(p, magicNumber, TURNS), magicNumber));

        addToBot(new ChannelAction(new MaatOrb(magicNumber)));
        addToBot(new SetPowerZeroAction(p, p, AttunePower.POWER_ID));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new SummonMaat();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG);
        }
    }

}
