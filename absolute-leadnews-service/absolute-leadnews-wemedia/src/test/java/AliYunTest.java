import com.absolute.common.aliyun.GreenImageScan;
import com.absolute.common.aliyun.GreenTextScan;
import com.absolute.common.aliyun.GreenTextScan2023;
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
//    @Autowired
//    private GreenTextScan greenTextScan;
//    @Autowired
//    private GreenImageScan greenImageScan;
//    @Autowired
//    private FileStorageService fileStorageService;
    @Autowired
    private GreenTextScan2023 greenTextScan2023;

    @Test
    public void testScanText() throws Exception {
        greenTextScan2023.textScan("耶稣基督");
    }

    @Test
    public void testScanImage() throws Exception {

    }
}
