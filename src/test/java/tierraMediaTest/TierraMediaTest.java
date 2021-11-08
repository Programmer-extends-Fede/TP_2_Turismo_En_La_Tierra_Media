package tierraMediaTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import tierraMediaConsola.TierraMedia;

public class TierraMediaTest {

	@Test
	public void tierraMediaConstruida() {
		TierraMedia tierraMedia = TierraMedia.getInstancia();
		
		assertNotNull(tierraMedia);
	}
	
	@Test 
	public void soloExisteUnaTierraMediaTest(){		
		TierraMedia tierraMedia = TierraMedia.getInstancia();
		TierraMedia otraTierraMedia = TierraMedia.getInstancia();
		
		assertNotNull(tierraMedia);
		assertSame(tierraMedia, otraTierraMedia);
	}
}
