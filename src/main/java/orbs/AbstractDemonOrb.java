package orbs;

import com.megacrit.cardcrawl.orbs.AbstractOrb;
import mod.RitualistMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractDemonOrb extends AbstractOrb {
    public static final Logger logger = LogManager.getLogger(RitualistMod.class.getName());
    public boolean isBanished = false;

    public AbstractDemonOrb()
    {
        super();


    }

    public void onBanish() {
        logger.info("banished orb abstract call");

    }
}


