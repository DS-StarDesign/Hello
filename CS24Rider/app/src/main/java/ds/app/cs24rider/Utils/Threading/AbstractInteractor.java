package ds.app.cs24rider.Utils.Threading;

import android.content.Context;
import ds.app.cs24rider.Utils.Threading.Executor;
import ds.app.cs24rider.Utils.Threading.MainThread;
import ds.app.cs24rider.Utils.PrefManager;

/*
 *  Created By MD. OLI ULLAH DEWAN 12-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public abstract class AbstractInteractor implements Interactor {

    public Executor mThreadExecutor;
    protected MainThread mMainThread;
    protected Context mContext;

    protected volatile boolean mIsCanceled;
    protected volatile boolean mIsRunning;

    public PrefManager mPrefManager;

    public AbstractInteractor(Executor threadExecutor, MainThread mainThread) {
        this.mThreadExecutor = threadExecutor;
        this.mMainThread = mainThread;
    }

    public AbstractInteractor(Executor threadExecutor, MainThread mainThread, Context context) {
        this.mThreadExecutor = threadExecutor;
        this.mMainThread = mainThread;
        this.mContext = context;
        this.mPrefManager = PrefManager.getInstance(mContext);
    }

    public abstract void run();

    public void cancel() {
        mIsCanceled = true;
        mIsRunning = false;
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public void onFinished() {
        mIsRunning = false;
        mIsCanceled = false;
    }

    public void execute() {
        this.mIsRunning = true;
        mThreadExecutor.execute(this);
    }
}