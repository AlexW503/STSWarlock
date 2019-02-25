package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import mod.RitualistMod;
import patches.MainEnum;
import powers.PossessionPower;

public class RudeInterruption extends CustomCard {
    /*
    * UNC Skill
    * 1E
    * If the enemy intends to attack apply 4 possession and they lose 10 strength this turn.
     */

    //Text Declaration

    public static final String ID = RitualistMod.makeID("RudeInterruption");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = RitualistMod.makePath("customImages/rude.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String TEXT = "That monster isn't attacking.";

    // /Text Declaration/
    //Stat Declaration

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = MainEnum.PURPLE;

    private static final int COST = 1;
    private static final int UPG = 4;
    private static final int POS = 4;
    private static final int STR = 8;



    // /Stat Declaration/


    public RudeInterruption() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = STR;
        magicNumber = baseMagicNumber;
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if (m == null || m.intent != AbstractMonster.Intent.ATTACK && m.intent != AbstractMonster.Intent.ATTACK_BUFF && m.intent != AbstractMonster.Intent.ATTACK_DEBUFF && m.intent != AbstractMonster.Intent.ATTACK_DEFEND) {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT, true));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PossessionPower(m, p, POS), POS));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, -magicNumber), -magicNumber, true, AbstractGameAction.AttackEffect.NONE));
           // if (!m.hasPower("Artifact")) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new GainStrengthPower(m, magicNumber), magicNumber, true, AbstractGameAction.AttackEffect.NONE));


        }


    }




    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new RudeInterruption();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG);
            initializeDescription();
        }
    }

}
