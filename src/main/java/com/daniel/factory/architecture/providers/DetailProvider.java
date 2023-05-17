package com.daniel.factory.architecture.providers;

import com.daniel.factory.architecture.store.DetailStore;
import com.daniel.factory.view.UIComponents.providersView.DetailProviderView;

public interface DetailProvider {

    public void setStore(DetailStore targetStore);

    public void setNewDelay(int newDelay);

    public void provideNewDetail() throws InterruptedException;

    public void setProviderView(DetailProviderView providerView);

    public int getCurrentDelay();

    public boolean notInterrupted();

}
