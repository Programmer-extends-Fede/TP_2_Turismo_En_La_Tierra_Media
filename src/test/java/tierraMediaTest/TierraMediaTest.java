package tierraMediaTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
		assertEquals(tierraMedia, otraTierraMedia);
		assertEquals(tierraMedia.hashCode(), otraTierraMedia.hashCode());
	}
}
