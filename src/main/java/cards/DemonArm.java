package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.RitualistMod;
import patches.MainEnum;
import powers.DemonArmPower;
import powers.DemonArmUpgPower;

public class DemonArm extends CustomCard {
    /*
    * UNC Power
    * 1E
    * Whenever you play an attack deal 5 damage to 1 random enemy.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("DemonArm");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/power.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = MainEnum.PURPLE;

    private static final int COST = 1;
    private static final int AMOUNT = 1;
    private static final int DMG = 5;
    private static final int UPG_DMG = 2;

    // /Stat Declaration/


    public DemonArm() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = DMG;
        magicNumber = baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!upgraded)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DemonArmPower(p, AMOUNT, magicNumber), AMOUNT));
        else
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DemonArmUpgPower(p, AMOUNT, magicNumber), AMOUNT));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DemonArm();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_DMG);
            initializeDescription();
        }
    }

}
