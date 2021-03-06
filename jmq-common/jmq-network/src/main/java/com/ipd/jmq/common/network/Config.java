package com.ipd.jmq.common.network;

import com.ipd.jmq.toolkit.os.Systems;

/**
 * 传输配置
 */
public abstract class Config {
    // 默认捕获信号量超时时间
    public static final int DEFAULT_ACQUIRE_TIMEOUT = 5000;
    // 默认发送数据包超时时间
    protected int sendTimeout = DEFAULT_ACQUIRE_TIMEOUT;
    // 工作线程
    protected int workerThreads = 4;
    // 异步回调线程数量
    protected int callbackThreads = Systems.getCores();
    // 选择器线程
    protected int selectorThreads = 1;
    // 通道最大空闲时间(毫秒)
    protected int maxIdleTime = 120 * 1000;
    // 表示是否允许重用Socket所绑定的本地地址
    protected boolean reuseAddress = true;
    // 关闭时候，对未发送数据包等待时间(秒)，-1,0:禁用,丢弃未发送的数据包;>0，等到指定时间，如果还未发送则丢弃
    protected int soLinger = -1;
    // 启用nagle算法，为真立即发送，否则得到确认或缓冲区满发送
    protected boolean tcpNoDelay = true;
    // 保持活动连接，定期心跳包
    protected boolean keepAlive = true;
    // socket读超时时间(毫秒)
    protected int soTimeout = 2000;
    // socket缓冲区大小
    protected int socketBufferSize = 1024 * 8;
    // 使用EPOLL，只支持Linux模式
    protected boolean epoll = false;
    // 支持EPOLL的最小内核版本(目前线上机器操作系统多个版本，有些不支持)
    protected String epollOsVersion = "2.6.32";
    // 最大单向请求并发数
    protected int maxOneway = 256;
    // 最大异步请求数
    protected int maxAsync = 128;
    // 数据包最大大小
    protected int frameMaxSize = 1024 * 1024 * 4 + 1024;

    public Config() {
    }

    public Config(Config config) {
        if (config == null) {
            throw new IllegalArgumentException("config can not be null");
        }
        setSendTimeout(config.getSendTimeout());
        setWorkerThreads(config.getWorkerThreads());
        setCallbackThreads(config.getCallbackThreads());
        setSelectorThreads(config.getSelectorThreads());
        setMaxIdleTime(config.getMaxIdleTime());
        setReuseAddress(config.isReuseAddress());
        setSoLinger(config.getSoLinger());
        setTcpNoDelay(config.isTcpNoDelay());
        setKeepAlive(config.isKeepAlive());
        setSoTimeout(config.getSoTimeout());
        setSocketBufferSize(config.getSocketBufferSize());
        setEpoll(config.isEpoll());
        setEpollOsVersion(config.getEpollOsVersion());
        setMaxOneway(config.getMaxOneway());
        setMaxAsync(config.getMaxAsync());
        setFrameMaxSize(config.getFrameMaxSize());
    }

    protected boolean supportEpoll() {
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        if (osName == null || osVersion == null) {
            return false;
        }
        if (osName.toLowerCase().startsWith("linux")) {
            if (epollOsVersion == null || epollOsVersion.isEmpty()) {
                return true;
            }
            String[] parts1 = osVersion.split("[\\.\\-]");
            String[] parts2 = epollOsVersion.split("[\\.\\-]");
            try {
                int v1, v2;
                for (int i = 0; i < parts2.length; i++) {
                    v2 = Integer.parseInt(parts2[i]);
                    v1 = Integer.parseInt(parts1[i]);
                    if (v1 > v2) {
                        return true;
                    }
                    if (v1 < v2) {
                        return false;
                    }
                }
                return true;
            } catch (NumberFormatException ignored) {
            }
        }
        return false;
    }

    public int getWorkerThreads() {
        return this.workerThreads;
    }

    public void setWorkerThreads(int workerThreads) {
        if (workerThreads > 0) {
            this.workerThreads = workerThreads;
        }
    }

    public int getCallbackThreads() {
        if (callbackThreads <= 0) {
            callbackThreads = 4;
        }
        return callbackThreads;
    }

    public void setCallbackThreads(int callbackThreads) {
        if (callbackThreads > 0) {
            this.callbackThreads = callbackThreads;
        }
    }

    public int getSelectorThreads() {
        return this.selectorThreads;
    }

    public void setSelectorThreads(int selectorThreads) {
        if (selectorThreads > 0) {
            this.selectorThreads = selectorThreads;
        }
    }

    public int getMaxIdleTime() {
        return this.maxIdleTime;
    }

    public void setMaxIdleTime(int maxIdleTime) {
        if (maxIdleTime > 0) {
            this.maxIdleTime = maxIdleTime;
        }
    }

    public boolean isReuseAddress() {
        return this.reuseAddress;
    }

    public void setReuseAddress(boolean reuseAddress) {
        this.reuseAddress = reuseAddress;
    }

    public int getSoLinger() {
        return this.soLinger;
    }

    public void setSoLinger(int soLinger) {
        this.soLinger = soLinger;
    }

    public boolean isTcpNoDelay() {
        return this.tcpNoDelay;
    }

    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    public boolean isKeepAlive() {
        return this.keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public int getSoTimeout() {
        return this.soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        if (soTimeout > 0) {
            this.soTimeout = soTimeout;
        }
    }

    public int getSocketBufferSize() {
        return this.socketBufferSize;
    }

    public void setSocketBufferSize(int socketBufferSize) {
        if (socketBufferSize > 0) {
            this.socketBufferSize = socketBufferSize;
        }
    }

    public int getMaxOneway() {
        return this.maxOneway;
    }

    public void setMaxOneway(int maxOneway) {
        this.maxOneway = maxOneway;
    }

    public int getMaxAsync() {
        return this.maxAsync;
    }

    public void setMaxAsync(int maxAsync) {
        this.maxAsync = maxAsync;
    }

    public int getFrameMaxSize() {
        return frameMaxSize;
    }

    public void setFrameMaxSize(int frameMaxSize) {
        if (frameMaxSize > 0) {
            this.frameMaxSize = frameMaxSize;
        }
    }

    public boolean isEpoll() {
        return epoll;
    }

    public void setEpoll(boolean epoll) {
        this.epoll = epoll && supportEpoll();
    }

    public String getEpollOsVersion() {
        return epollOsVersion;
    }

    public void setEpollOsVersion(String epollOsVersion) {
        this.epollOsVersion = epollOsVersion;
    }

    public int getSendTimeout() {
        return sendTimeout;
    }

    public void setSendTimeout(int sendTimeout) {
        if (sendTimeout > 0) {
            this.sendTimeout = sendTimeout;
        }
    }

    public static abstract class Builder<C extends Config, T extends Builder> {

        protected C config;

        public T workerThreads(final int workerThreads) {
            config.setWorkerThreads(workerThreads);
            return (T) this;
        }

        public T callbackExecutorThreads(final int callbackExecutorThreads) {
            config.setCallbackThreads(callbackExecutorThreads);
            return (T) this;
        }

        public T selectorThreads(final int selectorThreads) {
            config.setSelectorThreads(selectorThreads);
            return (T) this;
        }

        public T channelMaxIdleTime(final int channelMaxIdleTime) {
            config.setMaxIdleTime(channelMaxIdleTime);
            return (T) this;
        }

        public T reuseAddress(final boolean reuseAddress) {
            config.setReuseAddress(reuseAddress);
            return (T) this;
        }

        public T soLinger(final int soLinger) {
            config.setSoLinger(soLinger);
            return (T) this;
        }

        public T tcpNoDelay(final boolean tcpNoDelay) {
            config.setTcpNoDelay(tcpNoDelay);
            return (T) this;
        }

        public T keepAlive(final boolean keepAlive) {
            config.setKeepAlive(keepAlive);
            return (T) this;
        }

        public T soTimeout(final int soTimeout) {
            config.setSoTimeout(soTimeout);
            return (T) this;
        }

        public T socketBufferSize(final int socketBufferSize) {
            config.setSocketBufferSize(socketBufferSize);
            return (T) this;
        }

        public T epoll(final boolean epoll) {
            config.setEpoll(epoll);
            return (T) this;
        }

        public T maxOneway(final int maxOneway) {
            config.setMaxOneway(maxOneway);
            return (T) this;
        }

        public T maxAsync(final int maxAsync) {
            config.setMaxAsync(maxAsync);
            return (T) this;
        }

        public T frameMaxSize(final int frameMaxSize) {
            config.setFrameMaxSize(frameMaxSize);
            return (T) this;
        }

        public C build() {
            return config;
        }

    }

}