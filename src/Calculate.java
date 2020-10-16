import model.CalculateResult;
import model.DownloadResult;
import notification.NotifyService;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class Calculate implements Callable<CalculateResult> {

    private final DownloadResult download;
    private final NotifyService<CalculateResult> notifyService;

    public Calculate(DownloadResult downloadResult, NotifyService<CalculateResult> notifyService) {
        this.download = downloadResult;
        this.notifyService = notifyService;
    }

    public CalculateResult calculate() {
        CalculateResult result = new CalculateResult();
        result.id = download.id;
        for (int i = 0; i < 200000; i++) {
            int check = ThreadLocalRandom.current().nextInt(100000);
            if (download.check(check)) {
                result.found = true;
                notifyService.notify(result);
                return result;
            }
        }
        notifyService.notify(result);
        return result;
    }

    @Override
    public CalculateResult call() {
        return calculate();
    }
}
