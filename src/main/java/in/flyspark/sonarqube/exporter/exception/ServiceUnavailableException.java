package in.flyspark.sonarqube.exporter.exception;

public class ServiceUnavailableException extends Exception {

	private static final long serialVersionUID = 6301430710275487363L;

	public ServiceUnavailableException() {
	}

	public ServiceUnavailableException(String message) {
		super(message);
	}

}
