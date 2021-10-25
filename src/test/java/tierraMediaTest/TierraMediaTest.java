package tierraMediaTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import tierraMediaConsola.TierraMedia;

public class TierraMediaTest {

	@Test
	public void tierraMediaConstruida() {
		TierraMedia tierraMedia = TierraMedia.getTierraMedia();
		
		assertNotNull(tierraMedia);
	}
	
	@Test 
	public void soloExisteUnaTierraMediaTest(){		
		TierraMedia tierraMedia = TierraMedia.getTierraMedia();
		TierraMedia otraTierraMedia = TierraMedia.getTierraMedia();
		
		assertNotNull(tierraMedia);
		assertEquals(tierraMedia, otraTierraMedia);
		assertEquals(tierraMedia.hashCode(), otraTierraMedia.hashCode());
	}
}
