import com.absolute.common.aliyun.GreenImageScan;
import com.absolute.common.aliyun.GreenTextScan;
import com.absolute.file.service.FileStorageService;
import com.absolute.wemedia.WemediaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@SpringBootTest(classes = WemediaApplication.class)
@RunWith(SpringRunner.class)
public class AliYunTest {
    @Autowired
    private GreenTextScan greenTextScan;
    @Autowired
    private GreenImageScan greenImageScan;
    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void testScanText() throws Exception {
        Map map = greenTextScan.greeTextScan("我是好人。冰毒");
        System.out.println(map);
    }

    @Test
    public void testScanImage() throws Exception {

    }
}
