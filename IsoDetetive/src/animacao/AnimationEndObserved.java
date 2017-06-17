package animacao;

import java.util.ArrayList;

public interface AnimationEndObserved {
	public void animationEndRegister(AnimationEndObserver observer);
	public void animationEndUnRegister(AnimationEndObserver observer);
}
